using System.Drawing;

namespace HumTrack.Core.Calibration;

/// <summary>
/// Defines the structure for the Brown-Conrady lens distortion model used for camera calibration.
/// </summary>
public class LensDistortionModel
{
    // Radial distortion coefficients
    public double K1 { get; set; }
    public double K2 { get; set; }
    public double K3 { get; set; }
    public double K4 { get; set; }
    public double K5 { get; set; }
    public double K6 { get; set; }

    // Tangential distortion coefficients
    public double P1 { get; set; }
    public double P2 { get; set; }

    // Thin prism distortion coefficients
    public double S1 { get; set; }
    public double S2 { get; set; }
    public double S3 { get; set; }
    public double S4 { get; set; }
}
