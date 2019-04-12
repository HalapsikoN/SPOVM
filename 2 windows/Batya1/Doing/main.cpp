#include <stdio.h>
#include <iostream>
#include <Windows.h>

using namespace std;


int main(int argc, char** argv)
{
	HANDLE hereEvent = OpenEvent(EVENT_ALL_ACCESS, FALSE, "eventPlus");


	DWORD dwWaitResult = WAIT_FAILED;
	while (dwWaitResult != WAIT_OBJECT_0)
	{
		dwWaitResult = WaitForSingleObject(hereEvent, 100);
		cout << endl << "i'm here" << endl;
		
	}
	//ResetEvent(hereEvent);
	cout << endl << "now i'm here" << endl;
	return 0;
}