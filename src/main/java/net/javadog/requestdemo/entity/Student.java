package net.javadog.requestdemo.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Student实体类
 *
 * @author hdx
 * @version V1.0
 * @date 2021年10月18日
 */
@Getter
@Setter
@Data
public class Student {
    private String name;
    private Integer age;
}
