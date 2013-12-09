package dsd.controller;

import dsd.dao.MNDomainDAO;
import dsd.model.MNDomain;

public class MNDomainController
{
	public static MNDomain GetMNDomain()
	{
		return MNDomainDAO.GetMNDomain();
	}
}
