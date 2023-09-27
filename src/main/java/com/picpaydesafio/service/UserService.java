package com.picpaydesafio.service;

import com.picpaydesafio.domain.user.User;
import com.picpaydesafio.domain.user.UserType;
import com.picpaydesafio.dto.UserDTO;
import com.picpaydesafio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void validateTransaction(User sender, BigDecimal amount) throws Exception {

        if(sender.getUserType() != UserType.COMMON) {
            throw new Exception("Usuário não autorizado a realizar transação.");
        }

        if(sender.getBalance().compareTo(amount) < 0) { // comp balance < amount
            throw new Exception("Saldo insuficiente");
        }

    }

    // p evira acesso da transaction service com user repository
    public User findUSerById(Long id) throws Exception{
        return this.userRepository.findUserById(id).orElseThrow(() -> new Exception("Usuário não encontrado"));
    }

    public void saveUser(User user){
        this.userRepository.save(user);
    }

    public User createUser(UserDTO user) {
        User newUser = new User(user);
        this.saveUser(newUser);
        return newUser;
    }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

}
