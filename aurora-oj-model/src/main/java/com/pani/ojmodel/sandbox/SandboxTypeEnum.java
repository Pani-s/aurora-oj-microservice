package com.pani.ojmodel.sandbox;

import com.pani.ojmodel.enums.UserRoleEnum;
import org.apache.commons.lang3.ObjectUtils;

/**
 * @author Pani
 * @date Created in 2024/3/25 11:48
 * @description 类型
 */
public enum SandboxTypeEnum {
    /**
     * 沙箱类型
     */
    REMOTE("remote"),
    AI("ai"),
    EXAMPLE("example"),
    THIRD_PARTY("thirdParty");

    private final String value;

    SandboxTypeEnum(String value) {
        this.value = value;
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value
     * @return
     */
    public static SandboxTypeEnum getEnumByValue(String value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (SandboxTypeEnum anEnum : SandboxTypeEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }

    /**
     * 是否属于枚举
     *
     * @param value
     * @return
     */
    public static boolean isInEnum(String value) {
        if (ObjectUtils.isEmpty(value)) {
            return false;
        }
        for (SandboxTypeEnum anEnum : SandboxTypeEnum.values()) {
            if (anEnum.value.equals(value)) {
                return true;
            }
        }
        return false;
    }
}
