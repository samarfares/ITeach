package com.example.tcc.iteach;

import org.junit.Test;

import static org.junit.Assert.*;

public class SignUpInstructorActivityTest {

    @Test
    public void registerInstructor() {
        SignUpInstructorActivity e=new SignUpInstructorActivity();

         String l=e.registerInstructor();
        String h ="hi";
        assertEquals(h,e  );
    }
}