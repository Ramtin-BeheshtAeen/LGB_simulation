# How Physcis of the Simulation Work
1. Calculate Deriavatives
2. Integrate
3. Check the Impact
4. Reprat

## Calculating the Derivatives:
### Calculating the Drag:


### Calculating the deceleration:
The formula is:

$$a_x = -\frac{F_d \cdot v_x}{m \cdot |\vec{v}|}$$

**Starting from drag force:**

$$\vec{F}_d = -F_d \cdot \hat{v}$$

Drag always opposes motion, so it acts along the negative unit velocity vector:

$$\hat{v} = \frac{\vec{v}}{|\vec{v}|}$$

The x-component of this force is:

$$F_{d,x} = -F_d \cdot \frac{v_x}{|\vec{v}|}$$

Then Newton's second law gives:

$$a_x = \frac{F_{d,x}}{m} = -\frac{F_d \cdot v_x}{m \cdot |\vec{v}|}$$

The key insight is that $\dfrac{v_x}{|\vec{v}|}$ is the x-component of $\hat{v}$ — it projects the scalar drag force onto the x-axis proportionally to how much of the motion is in that direction.
