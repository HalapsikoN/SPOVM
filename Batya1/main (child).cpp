#include <iostream>
#include <conio.h>
#include <string>
#include <Windows.h>

int main(int argc, char*argv[])
{
	HANDLE pressEvent = OpenEvent(EVENT_ALL_ACCESS, FALSE, argv[0]);
	int i = 0;

	std::string output = "This is child process number ";
	while (argv[0][i] != '\0')
	{
		output += argv[0][i];
		i++;
	}	

	i = 0;
	while (TRUE) {
		while (output[i]!='\0')
		{
			std::cout << output[i];
			Sleep(20);
			i++;
		}
		std::cout << std::endl;
		i = 0;
		if (_kbhit())
		{
			PulseEvent(pressEvent);
			WaitForSingleObject(pressEvent, INFINITE);
		}
	}

	return 0;
}