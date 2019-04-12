#include <iostream>
#include <Windows.h>
#include <conio.h>
#include <stdio.h>
#include <vector>

//#define EVENT_NAME_SIZE 5;

void addProcess(std::vector<HANDLE> *eventVector, std::vector<PROCESS_INFORMATION> *processVector, int &processesNumber, LPCSTR childProcessName)
{
	char childEventName[5];
	_itoa_s(processesNumber, childEventName, 10);
	HANDLE tempEvent = CreateEvent(NULL, TRUE, FALSE, childEventName);
	
	if (!tempEvent)
	{
		std::cout << "SHOCK!!! EVENT DID NOT CREATE" << GetLastError() << std::endl;
		return;
	}
	eventVector->push_back(tempEvent);

	processesNumber++;

	STARTUPINFO startupInfo;
	PROCESS_INFORMATION processInformation;
	ZeroMemory(&startupInfo, sizeof(startupInfo));
	ZeroMemory(&processInformation, sizeof(processInformation));
	startupInfo.cb = sizeof(startupInfo);
	if (!CreateProcess(
		childProcessName,
		childEventName,
		NULL,
		NULL,
		FALSE,
		0,
		NULL,
		NULL,
		&startupInfo,
		&processInformation
	))
	{
		std::cout << "I DID't FIND A PROCESS" << std::endl;
		return;
	}
	processVector->push_back(processInformation);
}


void deleteProcess(std::vector<HANDLE> *eventVector, std::vector<PROCESS_INFORMATION> *processVector, int &processesNumber)
{
	TerminateProcess(processVector->back().hProcess, 0);
	CloseHandle(processVector->back().hThread);
	CloseHandle(processVector->back().hProcess);
	processVector->pop_back();
	CloseHandle(eventVector->back());
	eventVector->pop_back();
	processesNumber--;
}

void deleteAllProcess(std::vector<HANDLE> *eventVector, std::vector<PROCESS_INFORMATION> *processVector, int &processesNumber)
{
	while (!eventVector->empty())
	{
		deleteProcess(eventVector, processVector, processesNumber);
	}
}


int main(int argc, char** argv)
{
	int processesNumber = 1;
	LPCSTR defaultChildProcessName = TEXT("..\\Debug\\Doing.exe");
	HANDLE eventDone = OpenEvent(EVENT_ALL_ACCESS, FALSE, "Done");
	std::vector<HANDLE> eventVector;
	std::vector<PROCESS_INFORMATION> processVector;

	int i;
	while (TRUE)
	{
		if (_kbhit())
		{
			switch (_getch())
			{ 
			case'+':
			{
				addProcess(&eventVector, &processVector, processesNumber, (argc > 2) ? argv[1] : defaultChildProcessName);
				
				WaitForSingleObject(eventDone, INFINITE);
				break;
			}
			case'-':
			{
				if (!eventVector.empty())
				{
					//WaitForSingleObject(eventDone, INFINITE);
					deleteProcess(&eventVector, &processVector, processesNumber);
				}
				break;
			}
			case'q':
			{
				if (!eventVector.empty())
				{
					deleteAllProcess(&eventVector, &processVector, processesNumber);
				}
				return 0;
			}
			default:
				{
					break;
				}
			}
		}
		if (!eventVector.empty())
		{
			for (i = 0; i < eventVector.size(); ++i)
			{
				
				SetEvent(eventVector[i]);
				ResetEvent(eventVector[i]);
				WaitForSingleObject(eventVector[i], INFINITE);
				//std::cout << "done" << std::endl;
				//ResetEvent(eventVector[i]);
				//WaitForSingleObject(eventDone, INFINITE);
				//ResetEvent(eventDone);
			}
			std::cout << std::endl;
		}
	}




	return 0;
}


