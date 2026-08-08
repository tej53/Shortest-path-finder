package main.algorithm;

import java.util.*;

public class Dijkstra {
    
    static class Node implements Comparable<Node> {
        int row, col, distance;

        Node(int r, int c, int d){
            row = r;
            col = c;
            distance = d;
        }
        
        @Override
        public int compareTo(Node other){
            return this.distance - other.distance;
        } 
    }

    // 🔥 Result class for animation
    public static class Result{
        public List<int[]> visited;
        public List<int[]> path;

        public Result(List<int[]> visited, List<int[]> path){
            this.visited = visited;
            this.path = path;
        }
    }

    public static Result findShortestPath(int grid[][], int rows, int cols){

        int startRow = -1, startCol = -1;
        int endRow = -1, endCol = -1;

        // Find start and end
        for(int r=0; r<rows; r++){
            for(int c=0; c<cols; c++){
                if(grid[r][c] == 1){
                    startRow = r;
                    startCol = c;
                }
                if(grid[r][c] == 2){
                    endRow = r;
                    endCol = c;
                }
            }
        }

        // If start or end missing
        if(startRow == -1 || endRow == -1){
            return new Result(new ArrayList<>(), new ArrayList<>());
        }

        int dist[][] = new int[rows][cols];
        for(int[] row : dist){
            Arrays.fill(row, Integer.MAX_VALUE);
        }

        int parentRow[][] = new int[rows][cols];
        int parentCol[][] = new int[rows][cols];

        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(startRow, startCol, 0));
        dist[startRow][startCol] = 0;

        int dr[] = {-1, 1, 0, 0};
        int dc[] = {0, 0, -1, 1};

        boolean reached = false;

        // 🔥 Track visited order
        List<int[]> visitedOrder = new ArrayList<>();

        while(!pq.isEmpty()){
            Node current = pq.poll();

            visitedOrder.add(new int[]{current.row, current.col});

            if(current.row == endRow && current.col == endCol){
                reached = true;
                break;
            }

            for(int i=0; i<4; i++){
                int newRow = current.row + dr[i];
                int newCol = current.col + dc[i];

                if(newRow >= 0 && newRow < rows &&
                   newCol >= 0 && newCol < cols &&
                   grid[newRow][newCol] != 3){

                    int newDist = dist[current.row][current.col] + 1;

                    if(newDist < dist[newRow][newCol]){
                        dist[newRow][newCol] = newDist;
                        parentRow[newRow][newCol] = current.row;
                        parentCol[newRow][newCol] = current.col;
                        pq.add(new Node(newRow, newCol, newDist));
                    }
                }
            }
        }

        // If no path
        if(!reached){
            System.out.println("No Path Found!");
            return new Result(visitedOrder, new ArrayList<>());
        }

        // Build path
        List<int[]> path = new ArrayList<>();
        int r = endRow, c = endCol;

        while(!(r == startRow && c == startCol)){
            path.add(new int[]{r, c});
            int tempR = parentRow[r][c];
            int tempC = parentCol[r][c];
            r = tempR;
            c = tempC;
        }

        path.add(new int[]{startRow, startCol});
        Collections.reverse(path);

        return new Result(visitedOrder, path);
    }
}