package com.kalgooksoo.acl.service;

import com.kalgooksoo.acl.command.createAclEntryCommand;

public interface AclService {

    void createAclEntry(createAclEntryCommand command);

}