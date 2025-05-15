import re
import win32com.client

def get_validation_link():
    subject = "Validate Your Email to Complete Your USPS Online Account"
    outlook = win32com.client.Dispatch("Outlook.Application").GetNamespace("MAPI")
    inbox = outlook.GetDefaultFolder(6)  # 6 refers to the Inbox folder
    messages = inbox.Items
    messages.Sort("[ReceivedTime]", True)  # Sort by received time in descending order
    latest_message = messages.GetFirst()  # Get the most recent email

    # Check if the subject matches the expected email validation subject
    if subject in latest_message.Subject:
        body = latest_message.Body

        # Use regex to extract the validation link
        match = re.search(r"Click here to complete your e-mail validation <([^>]+)>", body)
        if match:
            return match.group(1)  # Return the extracted validation link
        else:
            return "Validation link not found in the email body."
    else:
        return "The latest email does not match the expected subject line."

# If this script is run directly, print the validation link
if __name__ == "__main__":
    print(get_validation_link())