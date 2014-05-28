package entities;

public class MovieEvaluation {
	public Score score;
	
	public int toInt()
	{
		if(score == Score.ONE) return 1;
		if(score == Score.TWO) return 2;
		if(score == Score.THREE) return 3;
		if(score == Score.FOUR) return 4;
		if(score == Score.FIVE) return 5;
		return 0;
	}
}

