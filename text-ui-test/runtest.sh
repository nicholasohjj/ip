#!/usr/bin/env bash

SRC_DIR="../src/main/java"
BIN_DIR="../bin"
TEST_DIR="text-ui-test"
INPUT_FILE="$TEST_DIR/input.txt"
EXPECTED_FILE="$TEST_DIR/EXPECTED.TXT"
ACTUAL_FILE="$TEST_DIR/ACTUAL.TXT"
EXPECTED_UNIX_FILE="$TEST_DIR/EXPECTED-UNIX.TXT"

# Create bin directory if it doesn't exist
mkdir -p "$BIN_DIR"

# Clean previous build files
rm -rf "$BIN_DIR"/*

# compile the code into the bin folder, terminates if error occurred
echo "Compiling source files..."
if ! javac -cp "$SRC_DIR" -Xlint:none -d "$BIN_DIR" "$SRC_DIR"/*.java; then
    echo "********** BUILD FAILURE **********"
    exit 1
fi
echo "Compilation successful."


# Delete previous test outputs
rm -f "$ACTUAL_FILE"

# Run the program with input redirection and capture output
echo "Running program..."
if ! java -classpath "$BIN_DIR" NiniNana < "$INPUT_FILE" > "$ACTUAL_FILE"; then
    echo "********** PROGRAM FAILURE **********"
    exit 1
fi
echo "Program executed successfully."

# Convert to UNIX format
echo "Converting files to UNIX format..."
cp "$EXPECTED_FILE" "$EXPECTED_UNIX_FILE"
dos2unix "$ACTUAL_FILE" "$EXPECTED_UNIX_FILE"

# Compare actual output with expected output
echo "Comparing output..."
if diff "$ACTUAL_FILE" "$EXPECTED_UNIX_FILE" > /dev/null; then
    echo "Test result: PASSED"
    exit 0
else
    echo "Test result: FAILED"
    echo "Differences found:"
    diff "$ACTUAL_FILE" "$EXPECTED_UNIX_FILE"
    exit 1
fi