package com.srllimited.srl.data;

/**
 * Created by RuchiTiwari on 12/26/2016.
 */

public class AddressData
{
	private String address;

	private String city;

	private String state;

	private String country;

	private String postalCode;

	private String knownName;

	private String completeAddress;

	public String getAddress()
	{
		return address;
	}

	public void setAddress(final String address)
	{
		this.address = address;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(final String city)
	{
		this.city = city;
	}

	public String getState()
	{
		return state;
	}

	public void setState(final String state)
	{
		this.state = state;
	}

	public String getCountry()
	{
		return country;
	}

	public void setCountry(final String country)
	{
		this.country = country;
	}

	public String getPostalCode()
	{
		return postalCode;
	}

	public void setPostalCode(final String postalCode)
	{
		this.postalCode = postalCode;
	}

	public String getKnownName()
	{
		return knownName;
	}

	public void setKnownName(final String knownName)
	{
		this.knownName = knownName;
	}

	public String getCompleteAddress()
	{
		return completeAddress;
	}

	public void setCompleteAddress(final String completeAddress)
	{
		this.completeAddress = completeAddress;
	}

	@Override
	public String toString()
	{
		return "AddressData{" + "address='" + address + '\'' + ", city='" + city + '\'' + ", state='" + state + '\''
				+ ", country='" + country + '\'' + ", postalCode='" + postalCode + '\'' + ", knownName='" + knownName
				+ '\'' + ", completeAddress='" + completeAddress + '\'' + '}';
	}
}
