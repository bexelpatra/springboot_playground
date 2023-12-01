package com.example.demo.my;

import java.lang.reflect.Proxy;

import org.checkerframework.checker.units.qual.C;
import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;

// import org.springframework.cglib.proxy.Proxy;

import com.example.demo.test_proxy.AboardApplicant;
import com.example.demo.test_proxy.Applicant;
import com.example.demo.test_proxy.CGLIBCallBackFilter;
import com.example.demo.test_proxy.CGLIBInvokeHandler;
import com.example.demo.test_proxy.CGLIBMethodInterceptor;
import com.example.demo.test_proxy.DaoFilter;
import com.example.demo.test_proxy.DomasticApplicant;
import com.example.demo.test_proxy.JDKInvokeHandler;
import com.example.demo.test_proxy.ServiceFilter;

public class ProxyTest {

    // https://velog.io/@suhongkim98/JDK-Dynamic-Proxy%EC%99%80-CGLib 참조
    public static void main(String[] args) {
            // JDKProxy();
            // CGLIBProxy();
            // EnhancerCallBack();
            EnhancerCallBackFilter();
        
    }

    // jdk proxy 에서 invokeHandler 방식
    private static void JDKProxy() {
        
        Applicant ab = (Applicant) Proxy.newProxyInstance(Applicant.class.getClassLoader(),new Class[]{Applicant.class}, new JDKInvokeHandler(new AboardApplicant("외국인 mr.kim")));
        Applicant dom = (Applicant) Proxy.newProxyInstance(Applicant.class.getClassLoader(),new Class[]{Applicant.class}, new JDKInvokeHandler(new DomasticApplicant("내국인 김씨")));

        StringBuilder sb = new StringBuilder();
        sb.append(ab.apply())
        .append("\n")
        .append(dom.apply());

        System.out.println(sb.toString());
    }
    // CGLib 에서 invokeHandler 방식
    private static void CGLIBProxy() {
        
        Applicant ab = (Applicant) org.springframework.cglib.proxy.Proxy.newProxyInstance(Applicant.class.getClassLoader(),new Class[]{Applicant.class}, new CGLIBInvokeHandler(new AboardApplicant("외국인 mr.kim")));
        Applicant dom = (Applicant) org.springframework.cglib.proxy.Proxy.newProxyInstance(Applicant.class.getClassLoader(),new Class[]{Applicant.class}, new CGLIBInvokeHandler(new DomasticApplicant("내국인 김씨")));

        StringBuilder sb = new StringBuilder();
        sb.append(ab.apply())
        .append("\n")
        .append(dom.apply());

        System.out.println(sb.toString());
    }
    // 
    private static void EnhancerCallBack(){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(DomasticApplicant.class);
        enhancer.setCallback(new CGLIBMethodInterceptor());
        DomasticApplicant dom = (DomasticApplicant) enhancer.create(new Class[]{String.class},new Object[]{"내국인이다"});
        
        System.out.println(dom.apply());
        dom.gogogo();
    }

    private static void EnhancerCallBackFilter(){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(DomasticApplicant.class);
        enhancer.setCallbackFilter(new CGLIBCallBackFilter());
        enhancer.setCallbacks(new Callback[]{new ServiceFilter(),new DaoFilter()});
        DomasticApplicant dom = (DomasticApplicant) enhancer.create(new Class[]{String.class},new Object[]{"내국인이다"});
        
        System.out.println(dom.apply());
        dom.gogogo();
        
        System.out.println("callbackfilter test");
        dom.abcDao();
        dom.abcService();
        
    }
    
}
