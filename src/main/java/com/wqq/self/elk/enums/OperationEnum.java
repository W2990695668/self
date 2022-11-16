package com.wqq.self.elk.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author Wangqq
 * @date 2022/6/17 - 16:40
 */
@AllArgsConstructor
@Getter
public enum OperationEnum {

    VISIT(0,"visit", "访问"),
    ADD_USER(1,"addUser", "添加用户"),
    ADD_PLATFORM(2,"addPlatform", "添加平台方"),
    ADD_CONSUMER(3,"addConsumer", "添加数据消费方"),
    ADD_DATA_ASSETS(4,"addDataAssets", "添加数据资产"),
    ADD_DATA_BUSINESS(5,"addDataBusiness", "添加数据交易"),
    ADD_DATA_TRANSFER(6,"addDataTransfer", "添加数据资产流转"),
    ADD_BLOCKCHAIN(7,"addBlockchain", "添加区块链");

    private Integer code;
    private String name;
    private String desc;


    public static OperationEnum getByName(String name){
        return Arrays.stream(OperationEnum.values()).filter(o->o.getName() == name).findFirst().get();
    }

}
