package com.microsservices.user.service;

import com.example.shoppingclient.exception.UserNotFoundException;
import com.microsservices.user.dto.UserDTO;
import com.microsservices.user.model.User;
import com.microsservices.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserDTO> getAll() {
        List<User> usuarios = userRepository.findAll();
        return usuarios
                .stream()
                .map(UserDTO::convert)
                .collect(Collectors.toList());
    }

    public UserDTO findById(long userId){
        Optional<User> usuario = userRepository.findById(userId);
        if (usuario.isPresent()){
            return UserDTO.convert(usuario.get());
        }
        throw new UserNotFoundException();
    }

    public UserDTO save(UserDTO userDTO){
        userDTO.setDataCadastro(new Date());
        userDTO.setKey(UUID.randomUUID().toString());
        User user = userRepository.save(User.convert(userDTO));
        return UserDTO.convert(user);
    }

    public UserDTO delete(long userId){
        Optional<User> user = userRepository.findById(userId);
        user.ifPresent(value -> userRepository.delete(value));
        return null;
    }

    public UserDTO findByCpf(String cpf){
        User user = userRepository.findBycpf(cpf);
        if (user != null){
            return UserDTO.convert(user);
        }
        throw new UserNotFoundException();
    }

    public List<UserDTO> queryByName(String name){
        List<User> usuarios = userRepository.queryByNomeLike(name);
        return usuarios
                .stream()
                .map(UserDTO::convert)
                .collect(Collectors.toList());
    }
}
