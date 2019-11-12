using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

/// <summary>
/// Summary description for CommonNames
/// </summary>

public class CommonNames
{

	#region Events
	public enum EventStatus { New = 0, Handled = 1 };
    public enum EventCode { ValueOverTolerance = 1, Panic = 2 };
	#endregion

	#region Actions
	public enum ActionType { ForceMeasure = 1 };
	public enum ActionStatus { New = 0, Completed = 1, Failed = 2 };
	#endregion

	public static string ActionTypeName(ActionType type)
	{
		switch (type)
		{
			case ActionType.ForceMeasure:
				return "Azonnali mérés";

			default:
				return "N/A";
		}
	}

	public static string ActionStatusName(ActionStatus status)
	{
		switch (status)
		{
			case ActionStatus.New:
				return "Új";

			case ActionStatus.Completed:
				return "Kész";

			case ActionStatus.Failed:
				return "Hiba";

			default:
				return "N/A";
		}
	}

	public static string EventStatusName(EventStatus status)
	{
		switch (status)
		{
			case EventStatus.New:
				return "Új";

			case EventStatus.Handled:
				return "Kezelt";
				
			default:
				return "N/A";
		}
	}

}