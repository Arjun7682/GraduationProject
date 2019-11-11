package ru.graduation.util;

import ru.graduation.model.Vote;
import ru.graduation.to.VoteTo;

public class VoteUtil {
    public static VoteTo asTo(Vote vote) {
        return new VoteTo(vote.getId(), vote.getDateTime(), vote.getRestaurant().getId());
    }
}
