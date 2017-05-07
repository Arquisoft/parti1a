package model;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import model.associtation.ComentarTest;
import model.associtation.ProponerTest;
import model.associtation.VotarCommentaryTest;
import model.associtation.VotarSuggestionTest;
import model.domain.SuggestionCategoryTest;
import model.domain.VoteCommentaryTest;
import model.domain.VoteSuggestionTest;
import model.domain.WordTest;

@RunWith(Suite.class)
@SuiteClasses({ 
	ComentarTest.class, 
	ProponerTest.class,
	VotarCommentaryTest.class,
	VotarSuggestionTest.class,
	VoteSuggestionTest.class,
	VoteCommentaryTest.class,
	WordTest.class,
	SuggestionCategoryTest.class
})
public class AllTest {

}
