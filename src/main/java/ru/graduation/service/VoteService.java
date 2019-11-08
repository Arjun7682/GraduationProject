package ru.graduation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.graduation.model.User;
import ru.graduation.model.Vote;
import ru.graduation.repository.RestaurantRepository;
import ru.graduation.repository.UserRepository;
import ru.graduation.repository.VoteRepository;

import java.util.List;

import static ru.graduation.util.ValidationUtil.checkDateTimeValid;
import static ru.graduation.util.ValidationUtil.checkNotFoundWithId;

@Service("voteService")
public class VoteService {

    private final VoteRepository voteRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public VoteService(VoteRepository voteRepository, UserRepository userRepository, RestaurantRepository restaurantRepository) {
        this.voteRepository = voteRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public Vote create(Vote vote, int userId) {
        Assert.notNull(vote, "vote must not be null");
        User user = userRepository.getOne(userId);
        vote.setUser(user);
        if (vote.isNew()) {
            return voteRepository.save(vote);
        } else {
            return get(vote.getId(), userId) == null ? null : voteRepository.save(vote);
        }

    }

    public Vote get(int id, int userId) {
        return checkNotFoundWithId(voteRepository.getVoteByIdAndUser_Id(id, userId), id);
    }

    /*public void update(Vote vote) {
        Assert.notNull(vote, "vote must not be null");
        checkNotFoundWithId(voteRepository.save(vote), user.getId());
    }*/

    public void delete(int id, int userId) {
        checkDateTimeValid();
        checkNotFoundWithId(voteRepository.delete(id, userId) != 0, id);
    }

    public List<Vote> getAll(int userId) {
        return voteRepository.getAllByUser_Id(userId);
    }
}
