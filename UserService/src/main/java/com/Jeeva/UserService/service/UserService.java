package com.Jeeva.UserService.service;

import com.Jeeva.UserService.Exception.UserAlreadyExistException;
import com.Jeeva.UserService.model.User;
import com.Jeeva.UserService.model.UserStatus;
import com.Jeeva.UserService.model.UserType;
import com.Jeeva.UserService.repository.UserRepository;
import com.Jeeva.UserService.request.CreateUserRequest;
import com.Jeeva.Utils.CommonConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jose4j.json.internal.json_simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Value("${user.authority}")
    private String userAuthority;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;


    public User createUser(CreateUserRequest createUserRequest) throws UserAlreadyExistException, JsonProcessingException {
        User checkForDuplicateUser = userRepository.findByPhoneNo(createUserRequest.getPhoneNo());

        if(checkForDuplicateUser!=null){
            throw new UserAlreadyExistException("Contact Number you gave is already associated with another account");
        }

        User newUser = createUserRequest.toUser();
        newUser.setAuthority(userAuthority);
        newUser.setUserStatus(UserStatus.ACTIVE);
        newUser.setUserType(UserType.USER);
        newUser.setPassword(encoder.encode(createUserRequest.getPassword()));

        userRepository.save(newUser);


        // once the user is created I have to send a notification

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(CommonConstants.USER_CREATION_TOPIC_NAME, StringUtils.isEmpty(newUser.getName())?"USER":newUser.getName());
        jsonObject.put(CommonConstants.USER_CREATION_TOPIC_EMAIL, newUser.getEmail());
        jsonObject.put(CommonConstants.USER_CREATION_TOPIC_PHONE_NO, newUser.getPhoneNo());
        jsonObject.put(CommonConstants.USER_CREATION_TOPIC_USERIDENTIFIER, newUser.getUserIdentifier());
        jsonObject.put(CommonConstants.USER_CREATION_TOPIC_USERIDENTIFIER_VALUE, newUser.getUserIdentifierValue());
        jsonObject.put(CommonConstants.USER_CREATION_TOPIC_ID, newUser.getId());
        kafkaTemplate.send(CommonConstants.USER_CREATION_TOPIC, objectMapper.writeValueAsString(jsonObject));
        return newUser;
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
//        System.out.println(username);
        User n = userRepository.findByPhoneNo(username);
//        System.out.println(n.getPhoneNo());
        return n;
    }
}
