#define _CRT_SECURE_NO_WARNINGS
#include <iostream>
#include <conio.h>
#include <stack>
#include <Windows.h>
#include <stdlib.h>

#define EVENT_NAME_SIZE 5

void startNewProcess(std::stack<PROCESS_INFORMATION>*, std::stack<HANDLE>*,int,LPCSTR);
void closeLastProcess(std::stack<PROCESS_INFORMATION>*, std::stack<HANDLE>*);
void closeAllProcesses(std::stack<PROCESS_INFORMATION>*, std::stack<HANDLE>*);

int main(int argc, char* argv[])
{
	int childProcessNumber = 1;
	LPCSTR defaultChildProcessName = TEXT("..\\Debug\\ChildProcess.exe");
	std::stack<PROCESS_INFORMATION> processStack;
	std::stack<HANDLE> eventStack;

	while (TRUE) {
		if (_kbhit())
		{
			switch (_getch())
			{
			case '+': startNewProcess(&processStack, &eventStack, childProcessNumber++, (argc >= 2) ? argv[1] : defaultChildProcessName);
				WaitForSingleObject(eventStack.top(), INFINITE); break;
			case '-': if (!processStack.empty()) { 
						closeLastProcess(&processStack, &eventStack); 
						childProcessNumber--; 
						if (!processStack.empty()) { 
							PulseEvent(eventStack.top());
							WaitForSingleObject(eventStack.top(), INFINITE);
						}
					  } break;
			case 'q': closeAllProcesses(&processStack, &eventStack); return 0;
			default: if (!processStack.empty()) { PulseEvent(eventStack.top()); WaitForSingleObject(eventStack.top(), INFINITE); } break;
			}
		}
		else
			continue;
	}


	return 0;
}

void startNewProcess(std::stack<PROCESS_INFORMATION>* processStack, std::stack<HANDLE>* eventStack, int childProcessNumber, LPCSTR childProcessName)
{
	char childEventName[EVENT_NAME_SIZE];
	_itoa(childProcessNumber, childEventName, 10);
	HANDLE eventTemp = CreateEvent(NULL, TRUE, FALSE, childEventName);
	if (!eventTemp)
	{
		std::cout << "Create Event Failed & Error NO - " << GetLastError() << std::endl;
		return;
	}
	eventStack->push(eventTemp);
	
	STARTUPINFO startupInfo;
	PROCESS_INFORMATION processInformation;
	ZeroMemory(&startupInfo, sizeof(startupInfo));
	ZeroMemory(&processInformation, sizeof(processInformation));
	startupInfo.cb = sizeof(startupInfo);
	BOOL bCreateProcess = NULL;
	bCreateProcess = CreateProcess(
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
	);
	if (bCreateProcess == FALSE)
	{
		std::cout << "Create Process Failed & Error NO - " << GetLast Error() << std::endl;
		return;
	}
	processStack->push(processInformation);

}

void closeLastProcess(std::stack<PROCESS_INFORMATION>* processStack, std::stack<HANDLE>* eventStack)
{
	TerminateProcess(processStack->top().hProcess, 0);
	CloseHandle(processStack->top().hProcess);
	CloseHandle(processStack->top().hThread);
	processStack->pop();
	CloseHandle(eventStack->top());
	eventStack->pop();

}

void closeAllProcesses(std::stack<PROCESS_INFORMATION>* processStack, std::stack<HANDLE>* eventStack)
{
	while (!processStack->empty())
	{
		closeLastProcess(processStack, eventStack);
	}

}