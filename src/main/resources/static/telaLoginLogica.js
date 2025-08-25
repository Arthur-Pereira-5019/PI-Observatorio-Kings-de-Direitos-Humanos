const hideButtons = document.querySelectorAll(".hideButton");

      const hideIcons = document.querySelectorAll(".hideIcon");

    hideIcons.forEach(icon => {
        icon.addEventListener("click", () => {
            const inputId = icon.getAttribute("data-target");
            const input = document.getElementById(inputId);

            if (input.type === "password") {
                input.type = "text";
                icon.src = "olhoAberto.png";
            } else {
                input.type = "password";
                icon.src = "olhoFechado.png";
            }
        });
    });