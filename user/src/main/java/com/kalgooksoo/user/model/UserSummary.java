package com.kalgooksoo.user.model;

import com.kalgooksoo.user.entity.Authority;
import com.kalgooksoo.user.entity.User;

import java.util.List;

public record UserSummary(User user, List<Authority> authorities) {}