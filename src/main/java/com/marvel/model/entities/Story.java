package com.marvel.model.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table
@Entity(name = "stories")
public class Story implements Serializable {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    @Column(name = "story_code", unique = true)
	private Long storyCode;
	private String name;
	@OneToOne(fetch = FetchType.LAZY)
	private StoryType type;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "story")
	private List<CharacterStory> characterStories;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "story")
	private List<CreatorStory> creatorStories;
	private static final long serialVersionUID = 5153818293305246365L;
}
