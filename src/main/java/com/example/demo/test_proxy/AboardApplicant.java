package com.example.demo.test_proxy;

public class AboardApplicant implements Applicant{

    private String name;
    public AboardApplicant(String name){
        this.name = name;
    }
    @Override
    public String apply() {
        // TODO Auto-generated method stub
        return String.format("외쿡인 이름 %s has applied", this.name);
    }

    @Override
    public String interview() {
        // TODO Auto-generated method stub
        return String.format("외쿡인 이름 %s has interview", this.name);
    }

    @Override
    public String result() {
        // TODO Auto-generated method stub
        return String.format("외쿡인 이름 %s get result", this.name);
    }
    
}
