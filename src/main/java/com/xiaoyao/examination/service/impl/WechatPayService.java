package com.xiaoyao.examination.service.impl;

import cn.felord.payment.wechat.v3.WechatApiProvider;
import cn.felord.payment.wechat.v3.model.Amount;
import cn.felord.payment.wechat.v3.model.PayParams;
import cn.felord.payment.wechat.v3.model.ResponseSignVerifyParams;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.xiaoyao.examination.exception.ErrorCode;
import com.xiaoyao.examination.exception.ExaminationException;
import com.xiaoyao.examination.properties.ExaminationProperties;
import com.xiaoyao.examination.service.OrderService;
import com.xiaoyao.examination.service.PayService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Service
@RequiredArgsConstructor
public class WechatPayService implements PayService {
    private final String tenantId = "pc";

    private final WechatApiProvider wechatApiProvider;
    private final ExaminationProperties examinationProperties;

    @Resource
    private OrderService orderService;

    @Override
    public String generateOrderId(int length) {
        return RandomUtil.randomString(length);
    }

    @Override
    public String createPayOrder(String paymentId, int money, String description,
                                 long orderId, long goodsId, int count) {
        PayParams payParams = new PayParams();
        payParams.setOutTradeNo(paymentId);
        Amount amount = new Amount();
        amount.setTotal(money);
        payParams.setAmount(amount);
        payParams.setDescription(description);
        payParams.setNotifyUrl(examinationProperties.getPayNotifyUrl());
        payParams.setAttach(StrUtil.format("{};{};{}", orderId, goodsId, count));
        return wechatApiProvider.directPayApi(tenantId).nativePay(payParams).getBody().get("code_url").asText();
    }

    @Override
    public void payCallback(HttpServletRequest request) {
        ResponseSignVerifyParams params = new ResponseSignVerifyParams();
        params.setWechatpaySerial(request.getHeader("Wechatpay-Serial"));
        params.setWechatpaySignature(request.getHeader("Wechatpay-Signature"));
        params.setWechatpayTimestamp(request.getHeader("Wechatpay-Timestamp"));
        params.setWechatpayNonce(request.getHeader("Wechatpay-Nonce"));
        try {
            params.setBody(readString(request.getInputStream()));
        } catch (IOException e) {
            throw new ExaminationException(ErrorCode.INVALID_PARAMS);
        }
        wechatApiProvider.callback(tenantId).transactionCallback(params, data -> {
            String[] split = data.getAttach().split(";");
            orderService.payOrder(Long.parseLong(split[0]), Long.parseLong(split[1]), Integer.parseInt(split[2]));
        });
    }

    private String readString(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line).append("\n");
        }

        bufferedReader.close();
        return stringBuilder.toString();
    }
}
