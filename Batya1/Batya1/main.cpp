#include <stdio.h>
#include <conio.h>
#include <iostream>
#include <Windows.h>

using namespace std;

HANDLE event;

int main(int argc, char** argv)
{
	//определяет оконный терминал, рабочий стол, стандартный дескриптор и внешний вид основного окна для нового процесса
	STARTUPINFO startupInfo;
	//обнуление
	ZeroMemory(&startupInfo, sizeof(startupInfo));
	startupInfo.cb = sizeof(startupInfo);

	//заполняет информацию о процессе и его первичном потоке
	PROCESS_INFORMATION processInfo;
	ZeroMemory(&processInfo, sizeof(processInfo));


	TCHAR ComandLine[] = TEXT("..\\Debug\\Doing.exe");
	TCHAR programName[] = TEXT("..\\Debug\\Doing.exe");
	char filePath[] = "file.txt";


	HANDLE event = CreateEvent(NULL, TRUE, TRUE, "eventPlus");
	if (event == NULL)
	{
		cout << "BAD" << endl;
		return 0;
	}

	ResetEvent(event);

	//char a;
	//cout << "======PARENT PROCESS START======" << endl;
	BOOL bl = NULL;
	//bl = CreateProcess(programName, NULL, NULL, NULL, NULL, 0, NULL, NULL, &startupInfo, &processInfo);
	//while(true)

	bl = CreateProcess(
		argc >= 2 ? argv[1] : programName,
		argc >= 3 ? argv[2] : ComandLine,
		NULL,
		NULL,
		FALSE,
		0,
		NULL,
		NULL,
		&startupInfo,
		&processInfo
	);
	if (bl == FALSE)
	{
		cout << "Process doesn't want to work :(" << endl;
	}
	//Sleep(1000);
	//SetEvent(event);
	
	int ch;
	char* q = "q";
	rewind(stdin);
	while (true)
	{
		//ch = getch();
		if (_getch()=='q')
		{
			SetEvent(event);
			break;
		}
	}
	

		if (WaitForSingleObject(processInfo.hProcess, 100000) != WAIT_FAILED)
	{
	cout << endl << processInfo.dwProcessId << "  " << processInfo.dwThreadId << endl;
	}
	else
	{
	cout << endl << "FAILED   " << GetLastError() << endl;
	}



	//закрыть описатели потока и процесса
	CloseHandle(processInfo.hProcess);
	CloseHandle(processInfo.hThread);


	return 0;
}