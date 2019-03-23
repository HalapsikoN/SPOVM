#include <iostream>
#include <fstream>
#include <Windows.h>


using namespace std;


int main(int argc, char* argv[])
{
	//определяет оконный терминал, рабочий стол, стандартный дескриптор и внешний вид основного окна для нового процесса
	STARTUPINFO startupInfo;
	//обнуление
	ZeroMemory(&startupInfo, sizeof(startupInfo));
	startupInfo.cb = sizeof(startupInfo);

	//заполняет информацию о процессе и его первичном потоке
	PROCESS_INFORMATION processInfo;
	ZeroMemory(&processInfo, sizeof(processInfo));

	TCHAR ComandLine[] =  TEXT("file.txt");
	TCHAR programName[] = TEXT("..\\Debug\\Daughter.exe");
	char filePath[] = "file.txt";

	char a;
	cout << "======PARENT PROCESS START======" << endl;
	BOOL bl = NULL;
	//bl = CreateProcess(programName, NULL, NULL, NULL, NULL, 0, NULL, NULL, &startupInfo, &processInfo);
	//while(true)
	{
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


		if (WaitForSingleObject(processInfo.hProcess, 10000) != WAIT_FAILED)
		{
			cout << endl << processInfo.dwProcessId << "  " << processInfo.dwThreadId << endl;
		}
		else
		{
			cout << endl << "FAILED   " << GetLastError() << endl;
		}
	}
	

	//закрыть описатели потока и процесса
	CloseHandle(processInfo.hProcess);
	CloseHandle(processInfo.hThread);

	fstream file("data.txt");

	if (!file.is_open())
	{
		cout << "Where is the file man?" << endl;
	}

	const int KOL = 10;

	int arr[KOL];

	cout << "Array in PARENT process: ";
	for (int i = 0; i < KOL; ++i)
	{
		file >> arr[i];
		cout << arr[i] << " ";
	}
	cout << endl;
	file.close();
	cout << "======PARENT PROCESS END======" << endl;
	return 0;
}