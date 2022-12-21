#!/bin/bash

# Creating build directory (if doesn't exist) and making it empty.
mkdir -p build
rm -rf build/*


compile_engine() {
  # Compile the engine code in 'meikyuu_engine' and create a shared object file
  # named engine.so in the build directory. Link the GLFW and GL libraries.
  echo "Compiling engine ..."
  g++ -o build/engine.so -fPIC -shared meikyuu_engine/*.cpp -lglfw -lGL
  echo "Compiling complete!"
}


compile_game() {
  # Compile the game code in 'src' and create an executable file
  # named game in the build directory. Include the engine header files
  # and link the GLFW and GL libraries.
  # The -Wl,-rpath=./build flag specifies the runtime library search path
  # so that the game executable can find the engine shared object file.
  echo "Compiling game ..."
  g++ -Wl,-rpath=./build -o build/game src_game/*.cpp -meikyuu_engine -lglfw -lGL
  echo "Compiling complete!"
}


# Prompt the user if engine needs compiling?
read -p "Do you want to compile the engine (Y/n)? " answer
if [[ $answer == "Y" || $answer == "y" ]]; then
  compile_engine()
else
  echo "Skipping compiling engine"
fi


# Prompt the user if engine needs compiling?
read -p "Do you want to compile the game (Y/n)? " answer
if [[ $answer == "Y" || $answer == "y" ]]; then
  compile_game()
else
  echo "Skipping compiling game"
fi


# Prompt the user to run the program
read -p "Do you want to run the program (Y/n)? " answer
if [[ $answer == "Y" || $answer == "y" ]]; then
  echo "Running game ..."
  ./build/game
fi