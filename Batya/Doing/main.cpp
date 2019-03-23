#include <Windows.h>
#include <iostream>
#include <conio.h>
#include <stdio.h>

using namespace std;


int main(int argc, char** argv)
{
	HANDLE eventDone = CreateEvent(NULL, TRUE, FALSE, "Done");
	HANDLE eventNow = OpenEvent(EVENT_ALL_ACCESS, FALSE, argv[0]);
	int i = 0;

	string output = "I want to say hello to your Batya for ";
	while (argv[0][i] != '\0')
	{
		output += argv[0][i];
		++i;
	}
	output += " times.";

	i = 0;
	//cout << "Done " << argv[0][i] << endl;
	//ResetEvent(eventDone);
	
	//WaitForSingleObject(eventDone, INFINITE);
	SetEvent(eventDone);
	ResetEvent(eventDone);
	while (TRUE)
	{
		SetEvent(eventNow);
		ResetEvent(eventNow);
		//cout << "Done 1" << argv[0][i] << endl;
		WaitForSingleObject(eventNow, INFINITE);
		
		
		i = 0;
		while (output[i] != '\0')
		{
			cout << output[i];
			Sleep(3);
			i++;
		}
		cout << endl;
		//SetEvent(eventDone);
		//ResetEvent(eventDone);
	}
	return 0;
}
