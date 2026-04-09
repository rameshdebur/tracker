using System.Drawing;

namespace HumTrack.Core.Tracking;

public class TrackingResult
{
    public float X { get; set; }
    public float Y { get; set; }
    public float Confidence { get; set; }
}

public interface ITrackingEngine
{
    TrackingResult TrackNextFrame();
}
