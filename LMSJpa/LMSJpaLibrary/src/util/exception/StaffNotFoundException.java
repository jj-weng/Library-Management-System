/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author jiajun
 */
public class StaffNotFoundException extends Exception {

    public StaffNotFoundException() {
    }
    
   
    public StaffNotFoundException(String data)
    {
        super(data);
    }
    
}
