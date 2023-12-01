package com.example.demo.test_proxy;

public class DomasticApplicant implements Applicant{

    private String name;
    public DomasticApplicant(String name){
        this.name = name;
    }
    @Override
    public String apply() {
        // TODO Auto-generated method stub
        return String.format("%s has applied", this.name);
    }

    @Override
    public String interview() {
        // TODO Auto-generated method stub
        return String.format("%s has interview", this.name);
    }

    @Override
    public String result() {
        // TODO Auto-generated method stub
        return String.format("%s get result", this.name);
    }

    public void gogogo(){
        System.out.println("나는 인터페이스에 없는 내국인이요");
    }
    public void abcDao(){
        System.out.printf("%s dao dao dao\n",name);
    }

    public void abcService(){
        System.out.printf("%s service service service\n",name);
    }
}
