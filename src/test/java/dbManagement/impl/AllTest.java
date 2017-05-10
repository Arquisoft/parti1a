package dbManagement.impl;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CategoryServiceImplTest.class, 
		CommentServiceImplTest.class, 
		SuggestionServiceImplTest.class,
		ParticipantServiceImplTest.class,
		UpdateInfoImplTest.class,
		WordServiceImplTest.class })
public class AllTest {

}
