package ru.graduation.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.graduation.AuthorizedUser;
import ru.graduation.View;
import ru.graduation.model.Vote;
import ru.graduation.service.VoteService;
import ru.graduation.to.VoteTo;
import ru.graduation.util.VoteUtil;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static ru.graduation.util.ValidationUtil.assureIdConsistent;
import static ru.graduation.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = VoteRestController.REST_VOTE_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteRestController {
    public static final String REST_VOTE_URL = "/rest/vote";
    private static final Logger log = LoggerFactory.getLogger(VoteRestController.class);

    private final VoteService service;

    @Autowired
    public VoteRestController(VoteService service) {
        this.service = service;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> createWithLocation(@Validated(View.Web.class) @RequestBody VoteTo voteTo, @AuthenticationPrincipal AuthorizedUser user) {
        checkNew(voteTo);
        log.info("create {} for user {}", voteTo, user);
        Vote created = service.create(voteTo, user.getId());
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_VOTE_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping("/{id}")
    public VoteTo get(@PathVariable int id, @AuthenticationPrincipal AuthorizedUser user) {
        log.info("get vote by id {}", id);
        return VoteUtil.asTo(service.get(id, user.getId()));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Validated(View.Web.class) @RequestBody VoteTo voteTo, @PathVariable int id, @AuthenticationPrincipal AuthorizedUser user) {
        log.info("update vote by id {}", id);
        assureIdConsistent(voteTo, id);
        service.update(voteTo, user.getId());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id, @AuthenticationPrincipal AuthorizedUser user) {
        log.info("delete vote by id {}", id);
        service.delete(id, user.getId());
    }

    @GetMapping
    public List<VoteTo> getAll(@AuthenticationPrincipal AuthorizedUser user) {
        log.info("get all votes by user {}", user);
        return service.getAll(user.getId()).stream().map(VoteUtil::asTo).collect(Collectors.toList());
    }
}
