package com.springpagetute.springpagetute.controller;

import com.springpagetute.springpagetute.deliminput.DelimInput;
import com.springpagetute.springpagetute.models.AuthenticationRequest;
import com.springpagetute.springpagetute.models.AuthenticationResponse;
import com.springpagetute.springpagetute.service.JWTUtilService;
import com.springpagetute.springpagetute.service.MyUserDetailsService;
import com.springpagetute.springpagetute.service.WordServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MyController {
    @Autowired
    private JWTUtilService jwtTokenUtil;
    @Autowired
    private MyUserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private WordServ wordServ;

    @GetMapping("/home")
    public String home() {
        return "Hi Ritik";
    }

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public String test(@RequestBody DelimInput delimInput) {
        return this.wordServ.test(delimInput);
    }
    //AUTHENTICATION
//    @RequestMapping(value = "/authenticate",method = RequestMethod.POST)
//    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)throws Exception{
//        try{
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),authenticationRequest.getPassword()));
//        }catch (BadCredentialsException e){
//            throw new Exception("Incorrect username or password",e);
//        }
//        final UserDetails userDetails= userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
//        final String jwt=jwtTokenUtil.generateToken(userDetails);
//
//        return ResponseEntity.ok(new AuthenticationResponse(jwt));
//    }
}

