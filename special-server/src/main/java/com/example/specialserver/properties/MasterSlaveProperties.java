package com.example.specialserver.properties;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class MasterSlaveProperties  extends MultipleServerProperties{
    /**
     * 从节点地址配置
     */
    private Set<String> slaveAddresses = new HashSet();
    /**
     * 主节点地址配置
     */
    private String masterAddress;
}
