package com.kalgooksoo.user.model;

import com.kalgooksoo.user.domain.Authority;
import com.kalgooksoo.user.domain.User;

import java.util.List;

public record UserSummary(User user, List<Authority> authorities) {}