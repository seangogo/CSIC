/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.dh.data;

import com.dh.entity.Role;
import org.springside.modules.test.data.RandomData;

public class RoleData {

	public static Role randomNewRole() {
		Role role = new Role();
		role.setRoleName(RandomData.randomName("role"));

		return role;
	}
}
