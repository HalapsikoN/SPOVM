# CMAKE generated file: DO NOT EDIT!
# Generated by "Unix Makefiles" Generator, CMake Version 3.13

# Delete rule output on recipe failure.
.DELETE_ON_ERROR:


#=============================================================================
# Special targets provided by cmake.

# Disable implicit rules so canonical targets will work.
.SUFFIXES:


# Remove some rules from gmake that .SUFFIXES does not remove.
SUFFIXES =

.SUFFIXES: .hpux_make_needs_suffix_list


# Suppress display of executed commands.
$(VERBOSE).SILENT:


# A target that is always out of date.
cmake_force:

.PHONY : cmake_force

#=============================================================================
# Set environment variables for the build.

# The shell in which to execute make rules.
SHELL = /bin/sh

# The CMake executable.
CMAKE_COMMAND = /home/halapsikon/Загрузки/clion-2019.1/bin/cmake/linux/bin/cmake

# The command to remove a file.
RM = /home/halapsikon/Загрузки/clion-2019.1/bin/cmake/linux/bin/cmake -E remove -f

# Escaping for special characters.
EQUALS = =

# The top-level source directory on which CMake was run.
CMAKE_SOURCE_DIR = /home/halapsikon/CLionProjects/father

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = /home/halapsikon/CLionProjects/father/cmake-build-debug

# Include any dependencies generated for this target.
include CMakeFiles/father.dir/depend.make

# Include the progress variables for this target.
include CMakeFiles/father.dir/progress.make

# Include the compile flags for this target's objects.
include CMakeFiles/father.dir/flags.make

CMakeFiles/father.dir/main.cpp.o: CMakeFiles/father.dir/flags.make
CMakeFiles/father.dir/main.cpp.o: ../main.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/home/halapsikon/CLionProjects/father/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_1) "Building CXX object CMakeFiles/father.dir/main.cpp.o"
	/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles/father.dir/main.cpp.o -c /home/halapsikon/CLionProjects/father/main.cpp

CMakeFiles/father.dir/main.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/father.dir/main.cpp.i"
	/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /home/halapsikon/CLionProjects/father/main.cpp > CMakeFiles/father.dir/main.cpp.i

CMakeFiles/father.dir/main.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/father.dir/main.cpp.s"
	/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /home/halapsikon/CLionProjects/father/main.cpp -o CMakeFiles/father.dir/main.cpp.s

# Object files for target father
father_OBJECTS = \
"CMakeFiles/father.dir/main.cpp.o"

# External object files for target father
father_EXTERNAL_OBJECTS =

father: CMakeFiles/father.dir/main.cpp.o
father: CMakeFiles/father.dir/build.make
father: CMakeFiles/father.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir=/home/halapsikon/CLionProjects/father/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_2) "Linking CXX executable father"
	$(CMAKE_COMMAND) -E cmake_link_script CMakeFiles/father.dir/link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
CMakeFiles/father.dir/build: father

.PHONY : CMakeFiles/father.dir/build

CMakeFiles/father.dir/clean:
	$(CMAKE_COMMAND) -P CMakeFiles/father.dir/cmake_clean.cmake
.PHONY : CMakeFiles/father.dir/clean

CMakeFiles/father.dir/depend:
	cd /home/halapsikon/CLionProjects/father/cmake-build-debug && $(CMAKE_COMMAND) -E cmake_depends "Unix Makefiles" /home/halapsikon/CLionProjects/father /home/halapsikon/CLionProjects/father /home/halapsikon/CLionProjects/father/cmake-build-debug /home/halapsikon/CLionProjects/father/cmake-build-debug /home/halapsikon/CLionProjects/father/cmake-build-debug/CMakeFiles/father.dir/DependInfo.cmake --color=$(COLOR)
.PHONY : CMakeFiles/father.dir/depend

