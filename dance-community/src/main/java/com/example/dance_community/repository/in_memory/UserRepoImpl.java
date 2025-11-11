//package com.example.dance_community.repository.in_memory;
//
//import com.example.dance_community.entity.User;
//import jakarta.annotation.PostConstruct;
//import org.springframework.stereotype.Repository;
//
//import java.util.Optional;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.atomic.AtomicLong;
//
//@Repository
//public class UserRepoImpl implements UserRepo {
//    private final ConcurrentHashMap<Long, User> idToUserMap = new ConcurrentHashMap<>();
//    private final ConcurrentHashMap<String, Long> emailToIdMap = new ConcurrentHashMap<>();
//    private final static AtomicLong userIdGen = new AtomicLong(0);
//
//    @PostConstruct
//    public void initData() {
//        User defaultUser = new User("user@example.com","string", "tester", "default_profile.png");
//        this.saveUser(defaultUser);
//    }
//
//    @Override
//    public User saveUser(User user){
//        if(user.getUserId() == null){
//            Long id = userIdGen.incrementAndGet();
//            //user = user.toBuilder().userId(id).build();
//        }
//        idToUserMap.put(user.getUserId(), user);
//        emailToIdMap.put(user.getEmail(), user.getUserId());
//        return user;
//    }
//
//    @Override
//    public boolean existsById(Long userId){
//        return idToUserMap.containsKey(userId);
//    }
//
//    @Override
//    public Optional<User> findById(Long userId){
//        return Optional.ofNullable(idToUserMap.get(userId));
//    }
//
//    @Override
//    public Optional<User> findByEmail(String email) {
//        Long userId = emailToIdMap.get(email);
//        if (userId == null) return null;
//        return findById(userId);
//    }
//
//    @Override
//    public void deleteUser(Long userId) {
//        User removedUser = idToUserMap.remove(userId);
//        if (removedUser != null) {
//            emailToIdMap.remove(removedUser.getEmail());
//        }
//    }
//}
