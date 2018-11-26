package com.wxz.utils;

import org.springframework.stereotype.Component;

import javax.xml.bind.*;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * @ClassName: XmlUtil
 * @Description: xml工具类
 * @Author: 王显政
 * @CreateDate: 2018/11/13 10:29
 * @UpdateUser:
 * @UpdateDate:
 * @Version: 0.0.1
 */
@Component
public class XmlUtil {
    /**
     * @Description: xml转成java对象
     * @Author: 王显政
     * @CreateDate: 2018/11/13 10:39
     * @UpdateUser:
     * @UpdateDate:
     * @Version: 0.0.1
     * @param xml xml字符串
     * @param C javaBean.class
     * @return
     */
    public static  <T> T xmlToJavaBean(String xml,Class<T> C) throws Exception {
        try {
            //1 声明上下文
            JAXBContext context=JAXBContext.newInstance(C);
            //2 创建能将xml转成javaBean的Unmarshaller对象
            Unmarshaller unmarshaller=context.createUnmarshaller();
            //3 将xml转成java
            T t= (T) unmarshaller.unmarshal(new StringReader(xml));
            //4 返回转换对象
            return t;
        }catch (Exception e){
            throw new Exception("xml转成java对象失败");
        }
    }
    /**
     * @Description: java对象转成xml
     * @Author: 王显政
     * @CreateDate: 2018/11/13 11:01
     * @UpdateUser:
     * @UpdateDate:
     * @Version: 0.0.1
     * @param obj java对象
     * @return
     */
    public static String javaBeanToXml(Object obj) throws Exception {
        try {
            //1 声明上下文
            JAXBContext context = JAXBContext.newInstance(obj.getClass());
            //2 创建能将javaBean转成xml的Unmarshaller对象
            Marshaller marshaller = context.createMarshaller();
            //3 设置是否格式化生成的xml串
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            //4 设置编码格式( 默认编码UTF-8)
            marshaller.setProperty(Marshaller.JAXB_ENCODING,"UTF-8");
            //5 创建字符串写对象
            StringWriter writer = new StringWriter();
            //6 把对象转成xml
            marshaller.marshal(obj,writer);
            //7 返回xml
            return writer.toString();
        }catch (Exception e){
            throw new Exception("java对象转成xml失败");
        }
    }
}
