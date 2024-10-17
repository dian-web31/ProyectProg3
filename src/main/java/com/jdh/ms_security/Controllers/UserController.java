package com.jdh.ms_security.Controllers;

import com.jdh.ms_security.Models.User;
import com.jdh.ms_security.Repositories.UserRepository;
import com.jdh.ms_security.Services.EncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository theuserRepository;

    @Autowired
    private EncryptionService theEncryptionService;

    @GetMapping("")
    public List<User> find(){
        return this.theuserRepository.findAll();
    }
    @GetMapping("{id}")
    public User findById(@PathVariable String id){
        User theUser=this.theuserRepository.findById(id).orElse(null);
        return theUser;
    }
    @PostMapping
    public User create(@RequestBody User newUser){
        newUser.setPassword(this.theEncryptionService.convertSHA256(newUser.getPassword()));
        return this.theuserRepository.save(newUser);
    }
    @PutMapping("{id}")
    public User update(@PathVariable String id, @RequestBody User newUser){
        User actualUser=this.theuserRepository.findById(id).orElse(null);
        if(actualUser!=null){
            actualUser.setName(newUser.getName());
            actualUser.setEmail(newUser.getEmail());
            actualUser.setPassword(this.theEncryptionService.convertSHA256(newUser.getPassword()));
            this.theuserRepository.save(actualUser);
            return actualUser;
        }else{
            return null;
        }

    }
    @DeleteMapping("{id}")
    public void delete(@PathVariable String id){
        User theUser=this.theuserRepository.findById(id).orElse(null);
        if (theUser!=null){
            this.theuserRepository.delete(theUser);
        }
    }
}
