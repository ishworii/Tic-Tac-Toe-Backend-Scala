### **Tic Tac Toe AI with Alpha-Beta Pruning**

This is a **Tic Tac Toe AI** implementation in **Scala** that uses the **MiniMax algorithm** with **Alpha-Beta Pruning**. The AI supports customizable grid sizes (e.g., 3x3, 4x4, etc.) and is optimized for performance with depth limiting and heuristic evaluations.

---

### **Features**
- **Customizable Grid Size**: Supports grids like 3x3, 4x4, 5x5, and more.
- **Optimal Play**: Uses Alpha-Beta Pruning to make intelligent moves while minimizing computation.
- **Heuristic Evaluation**: Implements heuristic scoring for faster decisions when depth limiting is applied.
- **Configurable Depth Limit**: Limits the depth of the search to improve performance for larger grids.
- **Winning Combination Caching**: Dynamically generates and caches winning combinations for efficient reuse.

---

### **How It Works**

1. **MiniMax Algorithm**:
    - Recursively evaluates all possible moves for both players to find the best outcome.
    - Uses Alpha-Beta Pruning to reduce the number of states explored by cutting off branches that don’t influence the result.

2. **Depth Limiting**:
    - Limits the depth of recursion to **6 moves** for larger grids to improve performance.

3. **Heuristic Evaluation**:
    - Scores partially filled boards based on:
        - Control of rows, columns, and diagonals.
        - Proximity to the center for better strategic positioning.

4. **Cached Winning Combinations**:
    - Precomputes and caches winning combinations for the specified grid size to avoid redundant computations.

---

### **Setup Instructions**

#### **Prerequisites**
- Install [Scala 3](https://www.scala-lang.org/download/) and [sbt](https://www.scala-sbt.org/).
- A Java runtime environment (JRE) is required to run Scala applications.

#### **Clone the Repository**
```bash
git clone https://github.com/your-repository/tic-tac-toe-ai.git
cd tic-tac-toe-ai
```

#### **Run the Application**
1. Compile the project:
   ```bash
   sbt compile
   ```
2. Run the application:
   ```bash
   sbt run
   ```

---

### **API Documentation**

This project includes an HTTP API for the Tic Tac Toe game. Below is the documentation for the `findBestMove` endpoint.

#### **Endpoint**
```
POST /findBestMove
```

#### **Request Body**
```json
{
  "cellSize": 3,
  "board": ["O", "X", null, "X", "O", null, null, null, null]
}
```
- **cellSize**: Integer representing the grid size (e.g., 3 for a 3x3 grid, 4 for a 4x4 grid).
- **board**: Array representing the current state of the board. Use `null` for empty cells, `"O"` for the AI player, and `"X"` for the opponent.

#### **Response**
```json
{
  "bestMove": 6
}
```
- **bestMove**: The index of the optimal move for the AI (0-based).

---

### **Code Structure**

```
.
├── src
│   ├── main
│   │   ├── scala
│   │   │   ├── com
│   │   │   │   └── ik
│   │   │   │       └── tictactoe
│   │   │   │           ├── MiniMax.scala    # AI logic with MiniMax and Alpha-Beta Pruning
│   │   │   │           ├── Routes.scala     # API routes for the Tic Tac Toe game
│   │   │   │           └── Server.scala     # Main server entry point
│   └── test
│       ├── scala
│       │   └── com
│       │       └── ik
│       │           └── tictactoe
│       │               └── MiniMaxTest.scala # Unit tests for the MiniMax algorithm
├── build.sbt                              # SBT build file with dependencies
└── README.md                              # Project documentation
```

---

### **Testing**

#### **Run Unit Tests**
```bash
sbt test
```

#### **Test Scenarios**
- 3x3 Grid:
  ```json
  {
    "cellSize": 3,
    "board": ["O", "X", "O", "X", null, null, "X", "O", null]
  }
  ```
  **Expected Response**:
  ```json
  {
    "bestMove": 4
  }
  ```

- 4x4 Grid:
  ```json
  {
    "cellSize": 4,
    "board": [
      "O", "X", null, null,
      "X", "O", "O", null,
      "X", null, null, null,
      null, null, null, null
    ]
  }
  ```
  **Expected Response**:
  ```json
  {
    "bestMove": 3
  }
  ```

---

### **Future Improvements**
- **Parallel Processing**: Evaluate possible moves concurrently for faster decision-making.
- **Dynamic Difficulty**: Introduce multiple difficulty levels by adjusting depth and heuristic weights.
- **Web-Based UI**: Integrate with a front-end framework (e.g., React) for a more interactive experience.

---

### **Contributors**
- [Your Name](https://github.com/your-profile)

Feel free to submit issues and feature requests!

---

### **License**
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.