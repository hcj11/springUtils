package com.example.demo;

/**
 * Created by hcj on 18-7-22
 */
public  class test {
  // 初始化方式,
  private Integer age =1;
  public test(){

  }
//  public test(Integer _age){
//    this.age=_age;
//  }
  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  static class subtest extends test{
    private String name;
    // 必须传给父类?
    public subtest(String _name){
      this.name=_name;
    }

    public String getName() {
       return name;
     }

     public void setName(String name) {
       this.name = name;
     }
   }
    public   static  void main(String[] args){
    //    test test = new test(12);
      // 若父类只有无参的构造函数, 默认子类调用父类无参构造函数,
      // 若父类有参的构造函数, 默认子类要调用父类的有参构造函数,
      subtest sub = new subtest("子");
      System.out.println(sub.getAge());


    }

}
