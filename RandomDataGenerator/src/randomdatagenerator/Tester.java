/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package randomdatagenerator;

/**
 *
 * @author gadre
 */
public class Tester {

    public static void main(String[] args) {
        RandomDataGenerator rdg = new RandomDataGenerator();

        for (int j = 0; j < 12; j++) {
            String sName = rdg.getPersonName();
            System.out.println(sName.toUpperCase());
            String sAddress = rdg.getAddress();
            System.out.println(sAddress);
            System.out.println("------");
        }

    }
}
