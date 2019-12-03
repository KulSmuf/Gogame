package board;

import static org.junit.Assert.*;

import org.junit.Test;

public class BoardTest {
	@Test
	public void testMakeMove() {
		Board testBoard = new Board(9);
		
		String move = testBoard.makeMove(2,2);
		assertEquals( move, "1" );
		assertEquals( testBoard.getMessageLog(), "2,2 0" );
		
		move = testBoard.makeMove(2,3);
		assertEquals( move, "1" );
		assertEquals( testBoard.getMessageLog(), "2,3 0" );

		move = testBoard.makeMove(3,3);
		assertEquals( move, "1" );
		assertEquals( testBoard.getMessageLog(), "3,3 0" );
		
		move = testBoard.makeMove(3,4);
		assertEquals( move, "1" );
		assertEquals( testBoard.getMessageLog(), "3,4 0" );
		
		move = testBoard.makeMove(2,4);
		assertEquals( move, "1" );
		assertEquals( testBoard.getMessageLog(), "2,4 0" );
		
		move = testBoard.makeMove(2,5);
		assertEquals( move, "1" );
		assertEquals( testBoard.getMessageLog(), "2,5 0" );
		
		move = testBoard.makeMove(1,3);
		assertEquals( move, "1 2,3" );
		assertEquals( testBoard.getMessageLog(), "1,3 1 2,3" );
	}
	
	@Test
	public void testCheckCorrectness() {
		Board testBoard = new Board(9);
		
		testBoard.makeMove(2,2);
		testBoard.makeMove(2, 1);
		testBoard.makeMove(3,3);
		testBoard.makeMove(3,4);
		
		assertTrue( testBoard.checkCorrectness(2, 4) );
		
		testBoard.makeMove(2,4);
		testBoard.makeMove(2,5);
		testBoard.makeMove(1,3);
		
		assertTrue( !testBoard.checkCorrectness(2, 3) );
	}
	
	@Test
	public void testCaptureStoneChain() {
		Board testBoard = new Board(9);
		
		testBoard.makeMove(0,1);
		testBoard.makeMove(0,0);
		testBoard.makeMove(0,2);
		testBoard.makeMove(1,1);
		testBoard.makeMove(0,4);
		testBoard.makeMove(1,2);
		testBoard.makeMove(1,4);
		
		String move = testBoard.makeMove( 0,3 );
		assertTrue(move.compareTo("2 0,1 0,2") == 0 || move.compareTo("2 0,2 0,1") == 0);
		assertTrue( testBoard.getMessageLog().compareTo( "0,3 " + move ) == 0 );
		
		testBoard.makeMove(0,7);
		testBoard.makeMove(0,6);
		testBoard.makeMove(1,7);
		testBoard.makeMove(1,6);
		testBoard.makeMove(2,7);
		testBoard.makeMove(0,5);
		testBoard.makeMove(2,6);
		testBoard.makeMove(1,5);
		move = testBoard.makeMove(2,5);
		assertEquals( move, "4 0,6 1,6 0,5 1,5" ); 
	}
	
}
