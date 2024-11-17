# Parallel Array Processor

This is a Java application designed to process three arrays filled with random numbers. The program uses multithreading to ensure efficient computation.

---

## Steps

- **Array Generation**:  
  Each array is filled with random numbers in the range [0; 1000] and saved to a separate file.  
- **Parallel Processing**:  
  - The first array retains only odd numbers.  
  - The second array divides all numbers by 3 (keeping only the integer part).  
  - The third array retains only numbers in the range [50; 250].  


---

## How to Run

### Steps to Run

1. **Clone or Download the Repository**  
   - Clone the repository (if hosted online):  
     ```bash
     git clone https://github.com/eLQeR/Parallel-Array-Processor.git
     ```  
   - Alternatively, download the code files and save them to a folder.

2. **Save the Main File**  
   - Ensure the main file is named `ParallelArrayProcessor.java`.

3. **Compile the Code**  
   - Compile the program using the following command:  
     ```bash
     javac ParallelArrayProcessor.java
     ```  
   - This will generate a `ParallelArrayProcessor.class` file in the same directory.

4. **Run the Program**  
   - Execute the compiled program:  
     ```bash
     java ParallelArrayProcessor
     ```  

5. **View Logs and Results**  
   - The program logs intermediate steps and outputs the final merged and filtered array in the terminal.  
   - Additionally, intermediate arrays will be saved in text files (`array1.txt`, `array2.txt`, `array3.txt`) in the program's directory.  
