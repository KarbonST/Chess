package model;

public record GameSnapshot(
        Board board,
        Team whiteTeam,
        Team blackTeam
)
{}
