package com.example.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = {"filedata"})
@NoArgsConstructor
@AllArgsConstructor

public class ItemImage {
	private long no;

	private String filename;
	private long filesize;
	private byte[] filedata; // BLOB
	private String filetype;

	private long itemno;
	
	private Date regdate;
}