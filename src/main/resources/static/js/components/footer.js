// =====================================================
// Footer component (static)
// =====================================================

export function renderFooter() {
    const footer = document.getElementById("footer");
    if (!footer) return;

    footer.innerHTML = `
        <footer class="footer">
            <div class="footer-logo">
                <img src="/assets/images/logo/logo.png" alt="SmartCare" height="40">
                <p>© 2025 SmartCare Solutions. All rights reserved.</p>
            </div>
            <div class="footer-column">
                <h4>Company</h4>
                <a href="#">About</a>
                <a href="#">Careers</a>
                <a href="#">Press</a>
            </div>
            <div class="footer-column">
                <h4>Support</h4>
                <a href="#">Account</a>
                <a href="#">Help Center</a>
                <a href="#">Contact</a>
            </div>
            <div class="footer-column">
                <h4>Legals</h4>
                <a href="#">Terms & Conditions</a>
                <a href="#">Privacy Policy</a>
                <a href="#">Licensing</a>
            </div>
        </footer>
    `;
}

document.addEventListener("DOMContentLoaded", renderFooter);
